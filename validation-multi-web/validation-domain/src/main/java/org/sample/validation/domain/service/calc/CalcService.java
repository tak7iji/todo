package org.sample.validation.domain.service.calc;

public interface CalcService {
	
	public int nop();
	
	public int nullArg(String arg);

	public int sum(int i, int j);
	
	public int throwException();
}
