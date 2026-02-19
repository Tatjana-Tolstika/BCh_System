package lv.venta.config;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lv.venta.model.MyAuthority;
import lv.venta.model.MyUser;

public class MyUserDetails implements UserDetails{
	private MyUser user;
	
	public MyUserDetails(MyUser inputUser) throws Exception{
		if(inputUser != null) {
			user = inputUser;
		}
		else {
			throw new Exception("User is null!");
		}
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		MyAuthority auth = user.getAuthority();
		
		ArrayList<SimpleGrantedAuthority> auths = new ArrayList<>();
		auths.add(new SimpleGrantedAuthority(auth.getTitle()));

		return auths;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

}
