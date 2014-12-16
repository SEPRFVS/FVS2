from PIL import Image
import os

path = "C:/git/sepr project/taxe/core/assets/trains"

os.chdir(path)

for file in os.listdir():
	if ".png" not in file:
		continue

	with Image.open(file) as im:
		im = im.resize((64,64))
		im.save(path + "/cursor/" + file)
