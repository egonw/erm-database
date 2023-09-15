---
layout: page
title: Identifiers
permalink: /identifiers/
---

<div class="tag-cloud">
<ul>
{% for erm_hash in site.data.erm %}
{% assign erm = erm_hash[1] %}
  <li>
    <a href="http://localhost:4000/erm-database/substance/{{ erm.id | replace: 'ERM', 'ERM/' }}">
      {{ erm.id }}
    </a>
  </li>
{% endfor %}
</ul>
</div>
