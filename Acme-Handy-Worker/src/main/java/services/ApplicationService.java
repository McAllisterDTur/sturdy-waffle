
package services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import repositories.ApplicationRepository;

@Service
@Transactional
public class ApplicationService {

	private ApplicationRepository	applicationRepository;
}
