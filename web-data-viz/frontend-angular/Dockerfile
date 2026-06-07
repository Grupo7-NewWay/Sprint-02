FROM node:20

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

RUN npx ng build
RUN npm install -g http-server

EXPOSE 4200

CMD ["http-server", "dist/frontend-angular/browser", "-p", "4200"]