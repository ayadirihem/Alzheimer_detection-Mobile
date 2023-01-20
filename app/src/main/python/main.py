#!/usr/bin/env python
# coding: utf-8

# In[18]:


from firebase import Firebase
import matplotlib.pyplot as plt
from matplotlib.backends.backend_agg import FigureCanvasAgg as FigureCanvas
import numpy as np
import pandas as pd
import cv2
from PIL import Image
import io
import base64
from io import BytesIO


# In[19]:


config = {
  "apiKey": "AIzaSyAU1wBFETZiDsDAhDqb3_31tnFwd8Nuatgy",
  "authDomain": "alzheimer--detection.firebaseapp.com",
  "databaseURL": "https://alzheimer--detection-default-rtdb.firebaseio.com",
  "storageBucket": "alzheimer--detection.appspot.com"
}

firebase = Firebase(config)
db = firebase.database()


# In[20]:


PatientSheet = []
patients = db.child("PatientSheet").get()
print(type(patients.val()))
data_p =patients.val()

print(dict(data_p))

print(data_p)
df = pd.DataFrame(data_p, columns=data_p.keys())
print(df)


# In[21]:


all_sheets = db.child("PatientSheet").get()
PatientsSheet = {}
PatientsSheet['age'] = []
PatientsSheet['asf'] = []
PatientsSheet['cdr'] = []
PatientsSheet['eTIV'] = []
PatientsSheet['educ'] = []
PatientsSheet['gender'] = []
PatientsSheet['group'] = []
PatientsSheet['mmse'] = []
PatientsSheet['nWBV'] = []
PatientsSheet['ses'] = []
for sheet in all_sheets.each():
    data = sheet.val()
    for key, value in data.items():
        if(key != 'fullName'):
            PatientsSheet[key].append(value)
df = pd.DataFrame(PatientsSheet)
with pd.option_context('display.max_rows', None, 'display.max_columns', None):  # more options can be specified also
    print(df)


# In[22]:


visual = df['group'].value_counts().plot(kind='bar', color=["#403b51", "#eabfb9"])
visual.legend()
visual


# In[23]:



fig = plt.figure(figsize=(20,10))
ay = fig.add_subplot(1,1,1)
visual = df['group'].value_counts().plot(kind='bar', color=["#403b51", "#eabfb9"], ax=ay)
visual.legend(prop={"size":20})
visual


# In[24]:


def main():
    # now we use this canvas to convert data to nump array..
    fig.canvas.draw()
    img = np.fromstring(fig.canvas.tostring_rgb(), dtype=np.uint8, sep='')
    img = img.reshape(fig.canvas.get_width_height()[::-1]+(3,)) #reshape data
    img = cv2.cvtColor(img, cv2.COLOR_RGB2BGR)

    #now it is converted to cv2 image ..
    #now i will conect this to PIL image and finally byte string ... and then we return this byte string to our java code..

    pil_im = Image.fromarray(img)
    buff = io.BytesIO()
    pil_im.save(buff, format="PNG")
    img_str = base64.b64encode(buff.getvalue())

    return ""+str(img_str,'utf-8')





# In[ ]:




