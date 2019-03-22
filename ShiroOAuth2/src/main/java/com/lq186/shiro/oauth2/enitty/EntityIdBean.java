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
    FileName: EntityIdBean.java
    Date: 2019/3/7
    Author: lq
*/
package com.lq186.shiro.oauth2.enitty;

import com.lq186.common.bean.EntityId;

import javax.persistence.*;

@MappedSuperclass
public class EntityIdBean implements EntityId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bzid", unique = true, nullable = false, length = 32)
    private String bzid;

    public void setId(Long id) {
        this.id = id;
    }

    public void setBzid(String bzid) {
        this.bzid = bzid;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getBzid() {
        return bzid;
    }
}
