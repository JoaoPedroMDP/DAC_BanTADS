FROM node:latest
COPY ./ /app
WORKDIR /app
RUN npm install
RUN npm i -g ts-node
RUN npm run build
CMD ["npm","start"]
# CMD ["ls"]