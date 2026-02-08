package pers.XiaoShadiao;

public class NMSLError extends Error {
	
	public NMSLError(String info) {
		super(info);
	}

	public NMSLError(String info,Throwable e) {
		super(info,e);
	}
	
	public NMSLError() {
		super();
	}
	
	public NMSLError(Throwable e) {
		super(e);
	}
	
}
