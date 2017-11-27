/*
 * Copyright 2017 pftakas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ftakas.stockit;

import com.ftakas.stockit.domain.UserDetails;
import com.ftakas.stockit.repository.UserDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class InitialDatabaseLoader implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(InitialDatabaseLoader.class);

    private UserDetailsRepository userDetailsRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public InitialDatabaseLoader(UserDetailsRepository userDetailsRepository,
                                 PasswordEncoder passwordEncoder) {
        this.userDetailsRepository = userDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userDetailsRepository.count() != 0) {
            logger.info("The users table is not empty. No action taken.");
            return;
        }

        UserDetails userDetails = new UserDetails();
        userDetails.setUsername("foobar");
        userDetails.setPassword(passwordEncoder.encode("barfoo"));
        userDetails.setEmail("salagator@gmail.com");

        userDetailsRepository.save(userDetails);
    }
}
