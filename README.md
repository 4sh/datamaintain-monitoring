# datamaintain-monitoring

Architecture decisions are recorded using ADRs. The template used for these records is [this one](https://github.com/joelparkerhenderson/architecture-decision-record/blob/main/templates/decision-record-template-by-michael-nygard/index.md).

## Launch your development environment
### Database
You can use the [docker-compose](modules/app-server/docker/docker-compose.yaml) in `modules/app-server/docker/docker-compose.yaml`. If you are at the root of the project, you can launch it using this command:

```bash
docker-compose --file modules/app-server/docker/docker-compose.yml up -d
```

Your database will be provided initial data to enable you to see what the application should behave even without creating data. 

### Server
Currently, the server uses Java 17 so please ensure that this is your current Java version.

#### Using IntelliJ
All run configurations are available in the folder [run](run).

## Launch tests

### Cypress
Please make sure you ran a `npm install` before trying to launch the Cypress tests. You can either run cypress with or without the GUI.

- With GUI:

```bash
cd modules/ui
npx cypress open
```

- Without GUI:

```bash
cd modules/ui
npx cypress run
```