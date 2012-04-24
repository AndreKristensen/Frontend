package no.nith.application;

import java.util.ArrayList;

import javax.ejb.Stateful;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import no.nith.domain.Person;

@Path("person")
@Stateful
public class PersonServiceBean {

	private ArrayList<Person> persons = new ArrayList<Person>();

	public PersonServiceBean() {
		persons.add(new Person("1", "Andre"));
		persons.add(new Person("2", "Knut"));
		persons.add(new Person("3", "Bendik"));
		persons.add(new Person("4", "Øyvind"));
	}

	@Path("/")
	@PUT
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public void addPerson(@FormParam("navn") String navn,
			@FormParam("id") String id) {
		persons.add(new Person(id, navn));
		// respons.setHeader("Added", "Person added" + navn);
	}

	@DELETE
	@Path("{id}")
	public void removePerson(@PathParam("id") String id,
			HttpServletResponse respons) {
		try {

			persons.remove(getById(id));
		} catch (NullPointerException np) {
			respons.setHeader("Not found", "person not found");
		}
		respons.setHeader("Delete", "Person deleted");
	}

	@GET
	@Path("hei")
	public String test() {
		return "hei";
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	@Path("{id}/{navn}")
	public Person getAPerson(@PathParam("id") String id,
			@PathParam("navn") String navn) {
		return new Person(id, navn);
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	@Path("all")
	public ArrayList<Person> getAll() {
		return persons;
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	@Path("{id}")
	public Person getById(@PathParam("id") String id) {

		for (Person p : persons) {
			if (p.getId().equals(id)) {
				return p;
			}
		}
		return null;
	}
}
