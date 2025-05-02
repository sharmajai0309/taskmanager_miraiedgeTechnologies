package com.MiraiEdge.Taskmanager.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailTask implements Serializable {
    private static final long serialVersionUID = 4233544975475420304L;
	private String to;
    private String subject;
    private String body;
}