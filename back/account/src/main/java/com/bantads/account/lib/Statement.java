package com.bantads.account.lib;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.bantads.account.transaction.models.TransactionDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Statement {
    public String startDate;
    public String endDate;
    public ArrayList<StatementDay> days;

    public Statement(ArrayList<TransactionDTO> dtos, Long from, Long to, RestService rest) {
        LinkedHashMap<Long, ArrayList<TransactionDTO>> organizedDtos = this.organizeByDay(dtos, rest);
        this.startDate = from.toString();
        this.endDate = to.toString();
        this.days = new ArrayList<>();

        for (Long day : organizedDtos.keySet()) {
            StatementDay newDay = new StatementDay(organizedDtos.get(day));
            this.days.add(newDay);
        }
    }

    private LinkedHashMap<Long, ArrayList<TransactionDTO>> organizeByDay(ArrayList<TransactionDTO> dtos,
            RestService rest) {
        LinkedHashMap<Long, ArrayList<TransactionDTO>> organizedDtos = new LinkedHashMap<Long, ArrayList<TransactionDTO>>();
        ZoneId utc = java.time.ZoneId.of("UTC");
        ZoneOffset utcOffset = ZoneOffset.ofHours(0);
        LinkedHashMap<String, String> transfersParticipants = new LinkedHashMap<String, String>();

        for (TransactionDTO transaction : dtos) {
            Instant inst = Instant.ofEpochMilli(transaction.getTimestamp());
            LocalDateTime dayAtMidnight = LocalDateTime.ofInstant(inst, utc)
                    .truncatedTo(java.time.temporal.ChronoUnit.DAYS);

            Long dayIdentifier = dayAtMidnight.toEpochSecond(utcOffset);
            if (!organizedDtos.containsKey(dayIdentifier)) {
                organizedDtos.put(dayIdentifier, new ArrayList<TransactionDTO>());
            }

            ArrayList<TransactionDTO> day = organizedDtos.get(dayIdentifier);
            if (transaction.isTransfer()) {
                // Apenas o ID é armazenado no banco de dados, então é necessário converter para
                // o nome do cliente
                String transferParticipant = "";
                if (transfersParticipants.get(transaction.getTransferParticipant()) == null) {
                    transferParticipant = this.getTransferParticipant(transaction.getTransferParticipant(), rest);
                    transfersParticipants.put(transaction.getTransferParticipant(), transferParticipant);
                }

                transferParticipant = transfersParticipants.get(transaction.getTransferParticipant());
                transaction.setTransferParticipant(transferParticipant);
            }
            day.add(transaction);
            organizedDtos.put(dayIdentifier, day);
        }

        return organizedDtos;
    }

    private String getTransferParticipant(String participantId, RestService rest) {
        // Uses RabbitMQ to get the participant name
        try {
            return rest.getCustomer(participantId).getNome();
        } catch (Exception e) {
            e.printStackTrace();
            return participantId;
        }

    }
}
