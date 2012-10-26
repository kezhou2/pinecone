package at.furti.springrest.client.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.testng.Assert;

@Entity(name = "City")
public class CityEntity {

	@Id
	@Column(name = "cityId")
	private Integer cityId;

	@Column(name = "plz")
	private String plz;

	@Column(name = "name")
	private String name;

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CityEntity)) {
			return false;
		}

		CityEntity other = (CityEntity) obj;

		Assert.assertEquals(other.getName(), getName(), "Name not equals");
		Assert.assertEquals(other.getPlz(), getPlz(), "PLZ not equals");

		return true;
	}
}
