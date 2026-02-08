//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;

public class LocalVariablesSorter extends MethodVisitor {
    private static final Type OBJECT_TYPE = Type.getObjectType("java/lang/Object");
    private int[] mapping;
    private Object[] newLocals;
    protected final int firstLocal;
    protected int nextLocal;
    private boolean changed;

    public LocalVariablesSorter(int var1, String var2, MethodVisitor var3) {
        this(327680, var1, var2, var3);
        if (this.getClass() != LocalVariablesSorter.class) {
            throw new IllegalStateException();
        }
    }

    protected LocalVariablesSorter(int var1, int var2, String var3, MethodVisitor var4) {
        super(var1, var4);
        this.mapping = new int[40];
        this.newLocals = new Object[20];
        Type[] var5 = Type.getArgumentTypes(var3);
        this.nextLocal = (8 & var2) == 0 ? 1 : 0;

        for(int var6 = 0; var6 < var5.length; ++var6) {
            this.nextLocal += var5[var6].getSize();
        }

        this.firstLocal = this.nextLocal;
    }

    public void visitVarInsn(int var1, int var2) {
        Type var3;
        switch (var1) {
            case 21:
            case 54:
                var3 = Type.INT_TYPE;
                break;
            case 22:
            case 55:
                var3 = Type.LONG_TYPE;
                break;
            case 23:
            case 56:
                var3 = Type.FLOAT_TYPE;
                break;
            case 24:
            case 57:
                var3 = Type.DOUBLE_TYPE;
                break;
            default:
                var3 = OBJECT_TYPE;
        }

        this.mv.visitVarInsn(var1, this.remap(var2, var3));
    }

    public void visitIincInsn(int var1, int var2) {
        this.mv.visitIincInsn(this.remap(var1, Type.INT_TYPE), var2);
    }

    public void visitMaxs(int var1, int var2) {
        this.mv.visitMaxs(var1, this.nextLocal);
    }

    public void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6) {
        int var7 = this.remap(var6, Type.getType(var2));
        this.mv.visitLocalVariable(var1, var2, var3, var4, var5, var7);
    }

    public AnnotationVisitor visitLocalVariableAnnotation(int var1, TypePath var2, Label[] var3, Label[] var4, int[] var5, String var6, boolean var7) {
        Type var8 = Type.getType(var6);
        int[] var9 = new int[var5.length];

        for(int var10 = 0; var10 < var9.length; ++var10) {
            var9[var10] = this.remap(var5[var10], var8);
        }

        return this.mv.visitLocalVariableAnnotation(var1, var2, var3, var4, var9, var6, var7);
    }

    public void visitFrame(int var1, int var2, Object[] var3, int var4, Object[] var5) {
        if (var1 != -1) {
            throw new IllegalStateException("ClassReader.accept() should be called with EXPAND_FRAMES flag");
        } else if (!this.changed) {
            this.mv.visitFrame(var1, var2, var3, var4, var5);
        } else {
            Object[] var6 = new Object[this.newLocals.length];
            System.arraycopy(this.newLocals, 0, var6, 0, var6.length);
            this.updateNewLocals(this.newLocals);
            int var7 = 0;

            int var8;
            for(var8 = 0; var8 < var2; ++var8) {
                Object var9 = var3[var8];
                int var10 = var9 != Opcodes.LONG && var9 != Opcodes.DOUBLE ? 1 : 2;
                if (var9 != Opcodes.TOP) {
                    Type var11 = OBJECT_TYPE;
                    if (var9 == Opcodes.INTEGER) {
                        var11 = Type.INT_TYPE;
                    } else if (var9 == Opcodes.FLOAT) {
                        var11 = Type.FLOAT_TYPE;
                    } else if (var9 == Opcodes.LONG) {
                        var11 = Type.LONG_TYPE;
                    } else if (var9 == Opcodes.DOUBLE) {
                        var11 = Type.DOUBLE_TYPE;
                    } else if (var9 instanceof String) {
                        var11 = Type.getObjectType((String)var9);
                    }

                    this.setFrameLocal(this.remap(var7, var11), var9);
                }

                var7 += var10;
            }

            var7 = 0;
            var8 = 0;

            for(int var12 = 0; var7 < this.newLocals.length; ++var12) {
                Object var13 = this.newLocals[var7++];
                if (var13 != null && var13 != Opcodes.TOP) {
                    this.newLocals[var12] = var13;
                    var8 = var12 + 1;
                    if (var13 == Opcodes.LONG || var13 == Opcodes.DOUBLE) {
                        ++var7;
                    }
                } else {
                    this.newLocals[var12] = Opcodes.TOP;
                }
            }

            this.mv.visitFrame(var1, var8, this.newLocals, var4, var5);
            this.newLocals = var6;
        }
    }

    public int newLocal(Type var1) {
        Object var2;
        switch (var1.getSort()) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                var2 = Opcodes.INTEGER;
                break;
            case 6:
                var2 = Opcodes.FLOAT;
                break;
            case 7:
                var2 = Opcodes.LONG;
                break;
            case 8:
                var2 = Opcodes.DOUBLE;
                break;
            case 9:
                var2 = var1.getDescriptor();
                break;
            default:
                var2 = var1.getInternalName();
        }

        int var3 = this.newLocalMapping(var1);
        this.setLocalType(var3, var1);
        this.setFrameLocal(var3, var2);
        this.changed = true;
        return var3;
    }

    protected void updateNewLocals(Object[] var1) {
    }

    protected void setLocalType(int var1, Type var2) {
    }

    private void setFrameLocal(int var1, Object var2) {
        int var3 = this.newLocals.length;
        if (var1 >= var3) {
            Object[] var4 = new Object[Math.max(2 * var3, var1 + 1)];
            System.arraycopy(this.newLocals, 0, var4, 0, var3);
            this.newLocals = var4;
        }

        this.newLocals[var1] = var2;
    }

    private int remap(int var1, Type var2) {
        if (var1 + var2.getSize() <= this.firstLocal) {
            return var1;
        } else {
            int var3 = 2 * var1 + var2.getSize() - 1;
            int var4 = this.mapping.length;
            if (var3 >= var4) {
                int[] var5 = new int[Math.max(2 * var4, var3 + 1)];
                System.arraycopy(this.mapping, 0, var5, 0, var4);
                this.mapping = var5;
            }

            int var6 = this.mapping[var3];
            if (var6 == 0) {
                var6 = this.newLocalMapping(var2);
                this.setLocalType(var6, var2);
                this.mapping[var3] = var6 + 1;
            } else {
                --var6;
            }

            if (var6 != var1) {
                this.changed = true;
            }

            return var6;
        }
    }

    protected int newLocalMapping(Type var1) {
        int var2 = this.nextLocal;
        this.nextLocal += var1.getSize();
        return var2;
    }
}
