package pers.XiaoShadiao.obfuscator.visitor;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import pers.XiaoShadiao.obfuscator.utils.Utils;

import java.util.Collections;
import java.util.List;

public class AttributeBreakerVisitor extends AbstractVisitor {

    public AttributeBreakerVisitor(byte[] bytes, String[] args) {
        super(bytes, args);
    }

    @Override
    public byte[] transfer(byte[] bytes) {

        int start = findClassAttributesCountOffset(bytes);

        bytes[start] = (byte) 0;
        bytes[start + 1] = (byte) 1;

        return bytes;
    }

    @Override
    public List<String> getVisitorTags() {
        return Collections.emptyList();
    }

    public static int findClassAttributesCountOffset(byte[] classBytes) {
        int offset = 8; // 跳过 magic(4) + minor_version(2) + major_version(2)

        // 1. 解析常量池
        int constantPoolCount = readUnsignedShort(classBytes, offset);
        offset += 2;
        for (int i = 1; i < constantPoolCount; i++) {
            int tag = classBytes[offset] & 0xFF;
            switch (tag) {
                case 1: // Utf8
                    int len = readUnsignedShort(classBytes, offset + 1);
                    offset += 3 + len;
                    break;
                case 3: case 4: // Integer, Float
                    offset += 5;
                    break;
                case 5: case 6: // Long, Double
                    offset += 9;
                    i++; // 占两个索引
                    break;
                case 7: case 8: case 16: case 19: case 20: // Class, String, MethodType, Module, Package
                    offset += 3;
                    break;
                case 9: case 10: case 11: case 12: case 17: case 18:
                    offset += 5;
                    break;
                case 15: // MethodHandle
                    offset += 4;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown constant pool tag: " + tag);
            }
        }

        // 2. 跳过 access_flags(2), this_class(2), super_class(2)
        offset += 6;

        // 3. 跳过接口表
        int interfacesCount = readUnsignedShort(classBytes, offset);
        offset += 2 + interfacesCount * 2;

        // 4. 跳过字段表
        int fieldsCount = readUnsignedShort(classBytes, offset);
        offset += 2;
        for (int i = 0; i < fieldsCount; i++) {
            offset += 6; // access_flags(2) + name_index(2) + descriptor_index(2)
            int attributesCount = readUnsignedShort(classBytes, offset);
            offset += 2;
            for (int j = 0; j < attributesCount; j++) {
                offset += 2; // attribute_name_index
                int attributeLength = readInt(classBytes, offset);
                offset += 4 + attributeLength;
            }
        }

        // 5. 跳过方法表
        int methodsCount = readUnsignedShort(classBytes, offset);
        offset += 2;
        for (int i = 0; i < methodsCount; i++) {
            offset += 6; // access_flags(2) + name_index(2) + descriptor_index(2)
            int attributesCount = readUnsignedShort(classBytes, offset);
            offset += 2;
            for (int j = 0; j < attributesCount; j++) {
                offset += 2; // attribute_name_index
                int attributeLength = readInt(classBytes, offset);
                offset += 4 + attributeLength;
            }
        }

        // 此时 offset 指向类属性 attributes_count 的起始位置
        return offset;
    }

    // 工具方法：读取无符号 short（2 字节）
    private static int readUnsignedShort(byte[] bytes, int offset) {
        return ((bytes[offset] & 0xFF) << 8) | (bytes[offset + 1] & 0xFF);
    }

    // 工具方法：读取 int（4 字节）
    private static int readInt(byte[] bytes, int offset) {
        return ((bytes[offset] & 0xFF) << 24)
                | ((bytes[offset + 1] & 0xFF) << 16)
                | ((bytes[offset + 2] & 0xFF) << 8)
                | (bytes[offset + 3] & 0xFF);
    }

}
