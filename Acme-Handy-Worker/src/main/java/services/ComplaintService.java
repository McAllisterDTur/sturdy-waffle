
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ComplaintRepository;

@Service
public class ComplaintService {

	@Autowired
	private ComplaintRepository	complaintRepository;


	public int getNumberOfTickers(final String ticker) {
		return this.complaintRepository.getNumberOfTickers(ticker);
	}
}
