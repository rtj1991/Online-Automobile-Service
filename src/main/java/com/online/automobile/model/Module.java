package com.online.automobile.model;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "module")
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "module", nullable = false, unique = true)
    private String module;

    @Column(name = "fa_icon")
    private String faIcon;

    private String route;

    @Column(name = "status_active")
    private Integer statusActive;

    @Column(name = "status_redirect")
    private Integer statusRedirect;

    @Column(name = "status_visibility")
    private Integer statusVisibility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    @JsonManagedReference
    private Module parent;

    @OneToMany(mappedBy = "parent")
    @JsonBackReference
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Module> modules;

    @ManyToMany(mappedBy = "modules")
    @JsonIgnore
    private List<Role> roles;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getFaIcon() {
        return faIcon;
    }

    public void setFaIcon(String faIcon) {
        this.faIcon = faIcon;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Integer getStatusActive() {
        return statusActive;
    }

    public void setStatusActive(Integer statusActive) {
        this.statusActive = statusActive;
    }

    public Integer getStatusRedirect() {
        return statusRedirect;
    }

    public void setStatusRedirect(Integer statusRedirect) {
        this.statusRedirect = statusRedirect;
    }

    public Integer getStatusVisibility() {
        return statusVisibility;
    }

    public void setStatusVisibility(Integer statusVisibility) {
        this.statusVisibility = statusVisibility;
    }

    public Module getParent() {
        return parent;
    }

    public void setParent(Module parent) {
        this.parent = parent;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {

        return "module{" +
                "id" + id +
                '}';
    }
}
