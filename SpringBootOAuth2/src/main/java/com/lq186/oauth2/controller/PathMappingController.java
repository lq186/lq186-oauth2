/*    
    Copyright ©2019 lq186.com 
 
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
 
        http://www.apache.org/licenses/LICENSE-2.0
 
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
/*
    FileName: PathMappingController.java
    Date: 2019/3/13
    Author: lq
*/
package com.lq186.oauth2.controller;

import com.lq186.common.log.Log;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PathMappingController {

    private static final Log log = Log.getLog(PathMappingController.class);

    @GetMapping(value = "/{page}**.html", produces = MediaType.TEXT_HTML_VALUE)
    public String path(@PathVariable("page") String page) {
        log.infof("Request for page [%s]", page);
        return page;
    }

}
