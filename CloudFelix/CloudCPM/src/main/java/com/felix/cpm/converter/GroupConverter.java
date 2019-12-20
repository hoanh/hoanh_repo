package com.felix.cpm.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.felix.domain.Group;
import com.felix.service.GroupService;


@FacesConverter(value = "groupConverter", managed = true)
public class GroupConverter implements Converter<Group> {

	@Inject
	private GroupService groupService;

	@Override
	public String getAsString(FacesContext context, UIComponent component, Group group) {

		if (group == null) {
			return "";
		}

		return group.getId()+"";
	}

	@Override
	public Group getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}

		return groupService.find(Long.parseLong(value));
	}

}