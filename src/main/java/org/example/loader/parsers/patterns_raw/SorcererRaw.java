package org.example.loader.parsers.patterns_raw;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SorcererRaw { public String name; public String rank; }
