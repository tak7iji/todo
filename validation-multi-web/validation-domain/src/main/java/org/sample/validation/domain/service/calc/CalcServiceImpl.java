package org.sample.validation.domain.service.calc;

import org.sample.validation.common.annotation.Audit;
import org.sample.validation.common.annotation.AuditParam;
import org.sample.validation.common.annotation.NonAuditParam;
import org.springframework.stereotype.Service;

@Service
public class CalcServiceImpl implements CalcService {

	@Audit
	@Override
	public int sum(@NonAuditParam int i, @AuditParam int j) {
		return i + j;
	}

	@Audit
	@Override
	public int nop() {
		return 0;
	}
	
	@Audit
	@Override
	public int nullArg(String arg) {
		return 0;
	}

	@Audit
	@Override
	public int throwException() {
		throw new RuntimeException("Wow");
	}

}
