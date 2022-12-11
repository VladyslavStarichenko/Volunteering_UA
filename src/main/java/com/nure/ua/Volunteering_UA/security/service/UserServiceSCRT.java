package com.nure.ua.Volunteering_UA.security.service;

import com.nure.ua.Volunteering_UA.dto.AuthenticationDto;
import com.nure.ua.Volunteering_UA.dto.AuthorizationDto;
import com.nure.ua.Volunteering_UA.exeption.CustomException;
import com.nure.ua.Volunteering_UA.model.user.Role;
import com.nure.ua.Volunteering_UA.model.user.Status;
import com.nure.ua.Volunteering_UA.model.user.User;
import com.nure.ua.Volunteering_UA.repository.role.RoleRepository;
import com.nure.ua.Volunteering_UA.repository.user.UserRepository;
import com.nure.ua.Volunteering_UA.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class UserServiceSCRT {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;



    @Autowired
    public UserServiceSCRT(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

//    public ResidentGetDto signUpResident(User user, Long flatId) {
//        Pattern passWordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,30}$");
//        Matcher matcherPassword = passWordPattern.matcher(user.getPassword());
//        if (userRepository.existsByUserName(user.getUserName())) {
//            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
//        } else if (!matcherPassword.matches()) {
//            throw new CustomException("Password should contain at least one capital letter, one lowercase letter, special character," +
//                    "length should be more or equals 8", HttpStatus.BAD_REQUEST);
//        } else {
//            Role roleUser = roleRepository.findByName("ROLE_RESIDENT");
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//            user.setRole(roleUser);
//            user.setStatus(Status.ACTIVE);
//            User registeredUser = userRepository.save(user);
//            log.info("IN register - user: {} successfully registered", registeredUser);
//
//            Resident resident = new Resident();
//            resident.setBill(Math.round(0*100.0)/100.0);
//            resident.setNotifications(new ArrayList<>());
//            Optional<Flat> flatById = flatRepository.findById(flatId);
//            flatById.ifPresent(resident::setFlat);
//            resident.setWastes(new ArrayList<>());
//            resident.setUser(registeredUser);
//            Resident residentToSave = residentRepository.save(resident);
//            return ResidentServiceImpl.fromResident(residentToSave);
//
//        }
//    }


//    public CleanerGetDto signUpCleaner(User user) {
//        User loggedInUser = getCurrentLoggedInUser();
//        Pattern passWordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,30}$");
//        Matcher matcherPassword = passWordPattern.matcher(user.getPassword());
//       if (!matcherPassword.matches()) {
//            throw new CustomException("Password should contain at least one capital letter, one lowercase letter, special character," +
//                    "length should be more or equals 8", HttpStatus.BAD_REQUEST);
//        } else {
//            Role roleUser = roleRepository.findByName("ROLE_CLEANER");
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//            user.setRole(roleUser);
//            user.setStatus(Status.ACTIVE);
//            User registeredUser = userRepository.save(user);
//            log.info("IN register - user: {} successfully registered", registeredUser);
//
//            Cleaner cleaner = new Cleaner();
//            cleaner.setUser(user);
////            HouseComplex houseComplex = complexServiceImpl.getComplex(loggedInUser);
////            List<HouseComplex>complexes = new ArrayList<>();
////            List<Cleaner>cleaners = new ArrayList<>();
////            cleaners.add(cleaner);
////            houseComplex.setCleaners(cleaners);
////            complexes.add(houseComplex);
////            cleaner.setComplexes(complexes);
//            Cleaner cleanerToSave = cleanerRepository.save(cleaner);
////            complexRepository.save(houseComplex);
//            return CleanerServiceImpl.fromCleaner(cleanerToSave);
//        }
//    }





    public Map<Object, Object> signUpOrganizationAdmin(User user) {
        Pattern passWordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,30}$");
        Matcher matcherPassword = passWordPattern.matcher(user.getPassword());
        if (userRepository.existsByUserName(user.getUserName())) {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (!matcherPassword.matches()) {
            throw new CustomException("Password should contain at least one capital letter, one lowercase letter, special character," +
                    "length should be more or equals 8", HttpStatus.BAD_REQUEST);
        } else {
            Role roleUser = roleRepository.findByName("ROLE_ORGANIZATION_ADMIN");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(roleUser);
            user.setEmail(user.getEmail());
            user.setStatus(Status.ACTIVE);
            User registeredUser = userRepository.save(user);
            log.info("IN register - user: {} successfully registered", registeredUser);
            String token = jwtTokenProvider.createToken(user.getUserName(), new ArrayList<>(Collections.singletonList(user.getRole())));
            String userNameSignedIn = user.getUserName();
            Map<Object, Object> response = new HashMap<>();
            response.put("username", userNameSignedIn);
            response.put("token", token);
            return response;
        }
    }

    public void blockComplexAdmin(String userName){

    }

    public Map<Object, Object> signIn(AuthorizationDto requestDto) throws AuthenticationException {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            Optional<User> user = userRepository.findUserByUserName(username);

            if (!user.isPresent()) {
                throw new UsernameNotFoundException("User with username: " + username + "wasn't found");
            }
//            if(user.get().getStatus() == Status.NONACTIVE){
//                throw new CustomException("You've been blocked - conctact the Admin of service", HttpStatus.BAD_REQUEST);
//            }
            log.info("IN signIn - user: {} successfully signedIN", userRepository.findUserByUserName(username));
            String token = jwtTokenProvider.createToken(username, new ArrayList<>(Collections.singletonList(user.get().getRole())));

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);
            response.put("role", user.get().getRole().getName());
            return response;
        } catch (AuthenticationException exception) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    public User getCurrentLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (userRepository.findUserByUserName(username).isPresent()) {
            return userRepository.findUserByUserName(username).get();
        }
        throw new UsernameNotFoundException("User with username: " + username + "wasn't found, you should authorize firstly") {
        };
    }


}
