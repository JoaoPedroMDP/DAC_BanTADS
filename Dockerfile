FROM node:16

RUN npm install -g npm@8.5.0
RUN npm install -g typescript@latest &&\
    npm install -g @angular/cli@latest

WORKDIR /app
CMD ["ng", "serve", "--host", "0.0.0.0"]
