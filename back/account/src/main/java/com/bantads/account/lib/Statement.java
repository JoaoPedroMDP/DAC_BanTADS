package com.bantads.account.lib;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.bantads.account.transaction.models.TransactionDTO;

public class Statement {
    public String startDate;
    public String endDate;
    public ArrayList<StatementDay> days;

    public Statement(ArrayList<TransactionDTO> dtos, Long from, Long to) {
        LinkedHashMap<Long, ArrayList<TransactionDTO>> organizedDtos = this.organizeByDay(dtos);
        this.startDate = from.toString();
        this.endDate = to.toString();
        this.days = new ArrayList<>();

        for (Long day : organizedDtos.keySet()) {
            StatementDay newDay = new StatementDay(organizedDtos.get(day));
            this.days.add(newDay);
        }
    }

    private LinkedHashMap<Long, ArrayList<TransactionDTO>> organizeByDay(ArrayList<TransactionDTO> dtos) {
        LinkedHashMap<Long, ArrayList<TransactionDTO>> organizedDtos = new LinkedHashMap<Long, ArrayList<TransactionDTO>>();
        ZoneId utc = java.time.ZoneId.of("UTC");
        ZoneOffset utcOffset = ZoneOffset.ofHours(0);

        for (TransactionDTO transaction : dtos) {
            Instant inst = Instant.ofEpochMilli(transaction.getTimestamp());
            LocalDateTime dayAtMidnight = LocalDateTime.ofInstant(inst, utc)
                    .truncatedTo(java.time.temporal.ChronoUnit.DAYS);

            Long dayIdentifier = dayAtMidnight.toEpochSecond(utcOffset);
            if (!organizedDtos.containsKey(dayIdentifier)) {
                organizedDtos.put(dayIdentifier, new ArrayList<TransactionDTO>());
            }

            ArrayList<TransactionDTO> day = organizedDtos.get(dayIdentifier);
            day.add(transaction);
            organizedDtos.put(dayIdentifier, day);
        }

        return organizedDtos;
    }
}
