---
layout: page
title: eNanoMapper Ontology
permalink: /enmo/
---
<div class="tag-cloud">
<ul>
{% for enmo_hash in site.data.enmo %}
{% assign enmo = enmo_hash[1] %}
  <li>
    <a href="{{ enmo.URI }}">
      {{ enmo.label }}
    </a>
  </li>
{% endfor %}
</ul>
</div>


<!-- TODO script needs to capture hierarchy. ontology terms will be added to _data from the ontology, with relevant fields and predicates. Href not to URI but to a collection of ERMs with the same annotation --!>