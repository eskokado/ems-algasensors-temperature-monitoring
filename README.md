# Temperature Monitoring Service

O **Temperature Monitoring Service** é o microserviço encarregado de observar e registrar o comportamento térmico dos sensores em tempo real.

## 📝 Responsabilidades

- Registro de logs de temperatura (`TemperatureLog`).
- Monitoramento do status dos sensores (`SensorMonitoring`).
- Disparo e consulta de alertas de temperatura (`SensorAlert`).
- Integração de dados baseada em TSID para rastreabilidade temporal.

## 🔧 Detalhes Técnicos

- **Framework**: Spring Boot 3.4.0
- **Linguagem**: Java 21
- **Porta**: 8082
- **ID Strategy**: TSID (Time-Sorted Unique Identifiers)

### API Endpoints (Exemplos)

- `GET /temperature-logs`: Consulta o histórico de medições.
- `GET /sensor-alerts`: Lista alertas ativos ou históricos.
- `GET /sensor-monitoring`: Verifica o status de monitoramento dos dispositivos.

## 🚀 Execução

```bash
./gradlew bootRun
```
