# tools 
 `tuanpv.tool.Tools -t=tsfw -o="output" -e="input\im_workflow\definition.xlsx"`
 
# Terasoluna Framework
 `-t=tsfw -o="output" -e="input\im_workflow\definition.xlsx"`

# Kindle book generator
 `tuanpv.tool.Tools -t=kindle -o="output" -e="input\kindle\book.properties"`
 `kindlegen.exe output\<book-code>\book.opf`
 + Read the book properties from extension
 + Download the content and save it like Kindle format
   https://www.amazon.com/gp/feature.html?docId=1000765211
 + Package to MOBI format via Kindlegen command

# How to clone:
 `git clone https://github.com/lacloi007/tools.git`

# Commit:
 `git commit -a -m "message"`
 `git push`

or
 `git add -A && git commit -m "comment" `