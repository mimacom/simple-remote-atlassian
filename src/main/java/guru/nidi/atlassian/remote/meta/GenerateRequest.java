/*
 * Copyright (C) 2013 Stefan Niederhauser (nidin@gmx.ch)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package guru.nidi.atlassian.remote.meta;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class GenerateRequest implements Serializable {
    private String url;
    private String template;
    private String templateSrc;
    private String style;
    private String styleSrc;
    private Set<String> stylesSecondary;
    private String language;
    private String projectKey;
    private String projectName;
    private String versions;
    private String docType;
    private String status;
    private String classification;
    private String author;
    private String collaborators;
    private String distribution;

    private Map<String, String> include;
    private boolean cache;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTemplateSrc() {
        return templateSrc;
    }

    public void setTemplateSrc(String templateSrc) {
        this.templateSrc = templateSrc;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyleSrc() {
        return styleSrc;
    }

    public void setStyleSrc(String styleSrc) {
        this.styleSrc = styleSrc;
    }

    public Set<String> getStylesSecondary() {
        return stylesSecondary;
    }

    public void setStylesSecondary(Set<String> stylesSecondary) {
        this.stylesSecondary = stylesSecondary;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(String collaborators) {
        this.collaborators = collaborators;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public Map<String, String> getInclude() {
        return include;
    }

    public void setInclude(Map<String, String> include) {
        this.include = include;
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }
}
