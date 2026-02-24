package lv.venta.service.impl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import lv.venta.config.MyUserDetails;
import lv.venta.model.Lecturers;
import lv.venta.model.MyAuthority;
import lv.venta.model.MyUser;
import lv.venta.repo.ILecturersRepo;
import lv.venta.repo.IMyAuthorityRepo;
import lv.venta.repo.IMyUserRepo;

@Service
public class MyUserDetailsManager implements UserDetailsManager{

	@Autowired
	private IMyUserRepo userRepo;
	
	@Autowired
	private IMyAuthorityRepo roleRepo;
	private PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		if(!userRepo.existsByUsername(username))
		{
			throw new UsernameNotFoundException("User not found");
		}
		
		MyUser userFromDB = userRepo.findByUsername(username);
		try
		{
			MyUserDetails userFromDBdetails = new MyUserDetails(userFromDB);
			return userFromDBdetails;
		}
		catch (Exception e) {
			throw new UsernameNotFoundException("User not found because it is null");
		}
		
		
	}
	
	//=====================USER CREATING=========================================

	@Override
	public void createUser(UserDetails userDetails) throws IllegalArgumentException{
		 if (userExists(userDetails.getUsername())) {
	            throw new IllegalArgumentException("User already exists!");
	        }

	        MyUser user = new MyUser();
	        user.setUsername(userDetails.getUsername());

	        // Paroles šifrēšana
	       
	        String encodedPassword = encoder.encode(userDetails.getPassword());
	        user.setPassword(encodedPassword);

	  //------------------------------------------------------------
	        String roleName = userDetails.getAuthorities().iterator().next().getAuthority();
	        MyAuthority role = roleRepo.findByTitle(roleName);
	        if (role == null) {
	            throw new IllegalArgumentException("Role not found: " + roleName);
	        }
	        user.setAuthority(role);

	        
	        
	        userRepo.save(user);

	}

	public void createLecturerUser(UserDetails userDetails, Lecturers lecturer) {
	    MyUser user = new MyUser();
	    user.setUsername(userDetails.getUsername());
	    user.setPassword(encoder.encode(userDetails.getPassword()));
	    
	    String roleName = userDetails.getAuthorities().iterator().next().getAuthority();
	    user.setAuthority(roleRepo.findByTitle(roleName));

	    if (lecturer != null) {
	        
	        user.setLecturer(lecturer);
	    }

	    userRepo.save(user);
	}
	//===========================================================================
	@Override
	public void updateUser(UserDetails userDetails) {
		MyUser existing = userRepo.findByUsername(userDetails.getUsername());
        if (existing == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        existing.setPassword(encoder.encode(userDetails.getPassword()));

        String roleName = userDetails.getAuthorities().iterator().next().getAuthority();
        MyAuthority role = roleRepo.findByTitle(roleName);
        if (role == null) {
            throw new IllegalArgumentException("Role not found: " + roleName);
        }
        existing.setAuthority(role);

        userRepo.save(existing);
		
	}

	@Override
	public void deleteUser(String username) {
		  MyUser user = userRepo.findByUsername(username);
	        if (user != null) {
	            userRepo.delete(user);
	        }
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();

	    MyUser user = userRepo.findByUsername(username);

	    if (user == null) {
	        throw new UsernameNotFoundException("User not found!");
	    }

	    if (!encoder.matches(oldPassword, user.getPassword())) {
	        throw new IllegalArgumentException("Old password is incorrect!");
	    }

	    user.setPassword(encoder.encode(newPassword));

	    userRepo.save(user);
	}

	@Override
	public boolean userExists(String username) {
		return userRepo.existsByUsername(username);
	}
}
