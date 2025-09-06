// Copyright (c) 2023-2025  Egon Willighagen <egon.willighagen@gmail.com>
//
// GPL v3

@Grab(group='io.github.egonw.bacting', module='managers-rdf', version='1.0.7')
@Grab(group='io.github.egonw.bacting', module='managers-ui', version='1.0.7')
@Grab(group='io.github.egonw.bacting', module='managers-zenodo', version='1.0.7')
@Grab(group='io.github.egonw.bacting', module='net.bioclipse.managers.jsoup', version='1.0.7')

import groovy.xml.XmlParser

bioclipse = new net.bioclipse.managers.BioclipseManager(".");
zenodo = new net.bioclipse.managers.ZenodoManager(".");
jsoup = new net.bioclipse.managers.JSoupManager(".");

xml = zenodo.getOAIPMHData(args[0])

// println xml

def oaiDatacite = new XmlParser().parseText(xml)

org = ""

oaiDatacite.GetRecord.record.header.setSpec.each { it ->
  if (it.text() == "user-nanosolveit") {
    org = "nanosolveit"
    orgURL = "https://nanosolveit.eu/"
  } else {
    println "<!-- Unknown organisation: ${it.text()} -->"
  }
}
if (org = "Undefined") {
  oaiDatacite.GetRecord.record.metadata.oai_datacite.payload.resource.relatedIdentifiers.relatedIdentifier.each { it ->
    if (it."@relationType" == "IsPartOf" &&
        it.text() == "https://zenodo.org/communities/asina/") {
      org = "asina"
    }
  }
}

record = oaiDatacite.GetRecord.record.metadata.oai_datacite.payload.resource
doi = record.identifier.text().toUpperCase()
title = record.titles.title[0].text()
date = record.dates.date[0].text()
license = record.rightsList.rights[0].@rightsURI
licenseTitle = record.rightsList.rights[0].text()
description = jsoup.removeHTMLTags(record.descriptions.description[0].text())
keywords = ""
if (record.subjects.subject.size() > 0) {
  keywords = record.subjects.subject.iterator().collect{it.text()}.join(", ")
}
url = "https://doi.org/${doi}"

keywordLine = ""
if (keywords.length() > 0) {
  keywordLine = """
    \"keywords\": \"${keywords}\","""
}

new File("work/${doi}.markdown").text = """---
layout: work
title: "Dataset: ${title}"
type:  Dataset
tag:  doi:${doi}
doi:  doi:${doi}
tags: ${org}
---
"""

new File("_data/work/${doi}.yml").text = """
title: "Dataset: ${title}"
type:  Dataset
tag:  doi:${doi}
doi:  doi:${doi}
uri: https://doi.org/${doi}
"""
