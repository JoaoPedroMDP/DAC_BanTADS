FROM node:latest
COPY ./ /app
WORKDIR /app
RUN npm i 
RUN npm i -g ts-node
RUN npm run build
CMD ["npm","start"]
# CMD ["ls"]