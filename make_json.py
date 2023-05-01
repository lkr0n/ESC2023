import json
from dataclasses import dataclass
import os
import pprint

@dataclass
class Country: 
    name: str
    image: str
    artist: str
    song: str
     
DOWNLOAD = False
# read in the esc candidates from the csv file that was generated from wikipedia
# html using fuckery.html
with open("esc_candidates.csv", "r") as csvfile:
    csv = csvfile.read()

# parse the csv and save the structured data for each row in a list
countries = [] 
for line in csv.split("\n"):
    name, image, artist, song, languages = line.split(";")
    escaped = name.replace(" ", "").lower()

    if DOWNLOAD:
        os.system(f"wget {image[2:]} -O 'app/src/main/res/drawable/{escaped}.png'")
    
    countries.append(
        Country(name, image, artist, song).__dict__
    )
    print(f"""Country(
    "{name}",
    R.drawable.{escaped},
    "{artist}",
    {song}
),""")

# format countries in json (the content of countries basically is almost valid
# json already, but we have to be standard compliant)
print(json.dumps(countries))
