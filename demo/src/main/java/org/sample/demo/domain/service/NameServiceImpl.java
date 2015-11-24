package org.sample.demo.domain.service;

import javax.inject.Inject;

import org.sample.demo.domain.repository.name.NameRepository;
import org.springframework.stereotype.Service;

@Service
public class NameServiceImpl implements NameService {
	
	@Inject
	NameRepository nameRepository;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return nameRepository.findOne();
	}

}
