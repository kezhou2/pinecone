package at.furti.springrest.client.repositories.find;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import at.furti.springrest.client.data.find.FindEntity;
import at.furti.springrest.client.data.find.FindRepository;
import at.furti.springrest.client.util.FindUtils;

@Test(groups = { "all", "find" })
@ContextConfiguration(locations = "classpath:at/furti/springrest/client/applicationContext.xml")
public class FindAllTest extends AbstractTestNGSpringContextTests {

	@Autowired(required = false)
	private FindRepository repository;

	/**
	 * 
	 */
	@Test
	public void repositoryInject() {
		Assert.assertNotNull(repository, "Repository was not injected");
	}

	/**
	 * 
	 */
	@Test(dependsOnMethods = "repositoryInject")
	public void findAll() {
		Iterable<FindEntity> entities = repository.findAll();

		Assert.assertNotNull(entities, "Entities not found");

		FindUtils.checkIterable(entities, Arrays.asList(FindUtils.create1(),
				FindUtils.create2(), FindUtils.create3(), FindUtils.create4()));
	}

	/**
	 * 
	 */
	@Test(dependsOnMethods = "repositoryInject")
	public void findAllIterable() {
		Iterable<FindEntity> entities = repository.findAll(Arrays.asList(
				new Integer(1), new Integer(3)));

		Assert.assertNotNull(entities, "Entities not found");

		FindUtils.checkIterable(entities, Arrays.asList(FindUtils.create1(),
				FindUtils.create3()));
	}

}
