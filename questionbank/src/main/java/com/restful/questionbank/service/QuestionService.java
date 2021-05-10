package com.restful.questionbank.service;

import java.util.List;

import javax.ws.rs.core.UriInfo;

import com.restful.questionbank.database.Database;
import com.restful.questionbank.model.Question;
import com.restful.questionbank.resource.QuestionResource;

public class QuestionService {
	
	private Database db=new Database();
	
	public List<Question> getAllQuestions(){
		return db.getAllQuestions();
	}

	public Question getQuestionById(long id) {
		return db.getQuestionById(id);
	}
	
	public void updateQuestion(Question question) {
		db.updateQuestion(question);
	}
	
	public Question addQuestion(Question question) {
		return db.addQuestion(question);
	}
	
	public void deleteQuestion(long id) {
		db.deleteQuestion(id);
	}
	
	public String getUriForSelf(UriInfo uriInfo,Question question) {
		String uri=uriInfo.getBaseUriBuilder()
				 .path(QuestionResource.class)
				 .path(Long.toString(question.getId()))
				 .build()
				 .toString();
		return uri;
	}
}
