package lv.venta.repo;

import lv.venta.model.MyUser;

public interface IMyUserRepo {

	public abstract boolean existsByUsername(String username);

	public abstract MyUser findByUsername(String username);

}
