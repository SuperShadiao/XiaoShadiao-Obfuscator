package pers.XiaoShadiao;

public class NMSLException extends RuntimeException {
	
	public NMSLException(String info) {
		super(info);
	}

	public NMSLException(String info,Throwable e) {
		super(info,e);
	}
	
	public NMSLException() {
		super();
	}
	
	public NMSLException(Throwable e) {
		super(e);
	}
	
}
