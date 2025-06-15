# UCP_Chatbot-ChronoLux

* pip install rasa==3.6.21
* pip install python-socketio==5.8.0 python-engineio==4.7.1
* pip install sanic==21.12.2 sanic-routing==0.7.2 sanic-cors==2.0.0

* Open 2 terminals:
+ first terminal, run: rasa run --enable-api --cors "*"
+ rest: rasa run actions --port 5055
