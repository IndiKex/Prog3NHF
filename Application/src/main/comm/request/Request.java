package main.comm.request;

import main.backend.continuos.RequestHandler;
import main.comm.asnwer.Answer;

public interface Request {
	public Answer accept(RequestHandler handler);
}
