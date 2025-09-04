ermid = args[0]
if (args.length > 1) {
  npoid = args[1]
} else {
  npoid = ""
}

content = new File("template.yml").text

content = content.replace("TEMPLATE", ermid)

if (npoid == "NPO_1486") {
  content += """chemicalComposition: TiO2
a: npo:NPO_1486"""
} else if (npoid == "NPO_1541") {
  content += """chemicalComposition: ...
a: npo:NPO_1541"""
}

content = content.replace("TEMPLATE", ermid)

new File("${ermid}.yml").text = content
