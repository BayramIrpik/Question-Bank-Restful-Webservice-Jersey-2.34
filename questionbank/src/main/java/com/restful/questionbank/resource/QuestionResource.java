package com.restful.questionbank.resource;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.restful.questionbank.model.Question;
import com.restful.questionbank.service.QuestionService;

@Path("/questions")
@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
public class QuestionResource {

	private QuestionService qs=new QuestionService();
	
	@GET
	public Response getAllQuestions() {
		
		List<Question> listQuestions =qs.getAllQuestions();
		GenericEntity<List<Question>> list = new GenericEntity<List<Question>>(listQuestions) {};
		return Response.ok(list).build();
	}
	
	@GET
	@Path("/{id}")
	public Response getQuestion(@PathParam("id") long id,@Context UriInfo uriInfo) {
		Question question=qs.getQuestionById(id);
		question.addLink(qs.getUriForSelf(uriInfo, question), "self");
		return Response.ok(question).build();
	}
	
	@POST
	public Response addQuestion(Question question,@Context UriInfo uriInfo) {
		Question newQuestion=qs.addQuestion(question);
		String newId=String.valueOf(newQuestion.getId());
		URI uri=uriInfo.getAbsolutePathBuilder().path(newId).build();
		
		return  Response.created(uri)
				.entity(newQuestion)
				.build();
	}
	
	@PUT
	@Path("/{id}")
	public Response updateQuestion(@PathParam("id") long id,Question question) {
		qs.updateQuestion(question);
		
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/{id}")
	public void deleteQuestion(@PathParam("id") long id) {
		qs.deleteQuestion(id);
	}
}
