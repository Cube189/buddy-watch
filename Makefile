-include .env

DOCKER_COMPOSE ?= docker-compose
GRADLE ?= gradle
PSQL ?= psql

build:
	$(GRADLE) build

test:
	$(GRADLE) test

restart: down up
.PHONY: restart

restart-deps: down up-deps
.PHONY: restart-deps

up:
	$(GRADLE) bootJar
	$(DOCKER_COMPOSE) up --build --remove-orphans -d
.PHONY: up

up-deps:
	$(DOCKER_COMPOSE) up --remove-orphans -d --scale backend=0
.PHONY: up-deps

down:
	$(DOCKER_COMPOSE) down --remove-orphans
	$(GRADLE) clean
.PHONY: down

clean:
	$(GRADLE) clean
	$(DOCKER_COMPOSE) rm --force --stop
.PHONY: clean

ps:
	$(DOCKER_COMPOSE) ps
.PHONY: ps

config:
	$(DOCKER_COMPOSE) config
.PHONY: config

logs:
	$(DOCKER_COMPOSE) logs -f
.PHONY: logs

connect-db:
	$(PSQL) -h 127.0.0.1 -U postgres
.PHONY: connect-db
