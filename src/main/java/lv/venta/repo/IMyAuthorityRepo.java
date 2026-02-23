package lv.venta.repo;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.MyAuthority;

public interface IMyAuthorityRepo extends CrudRepository<MyAuthority, Long>{

	public abstract MyAuthority findByTitle(String roleName);

}
