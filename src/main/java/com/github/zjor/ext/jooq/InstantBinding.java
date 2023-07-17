package com.github.zjor.ext.jooq;

import lombok.NonNull;
import org.jooq.Binding;
import org.jooq.BindingGetResultSetContext;
import org.jooq.BindingGetSQLInputContext;
import org.jooq.BindingGetStatementContext;
import org.jooq.BindingRegisterContext;
import org.jooq.BindingSQLContext;
import org.jooq.BindingSetSQLOutputContext;
import org.jooq.BindingSetStatementContext;
import org.jooq.Converter;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.TimeZone;

public class InstantBinding implements Binding<LocalDateTime, Instant> {
    private static final Converter<LocalDateTime, Instant> CONVERTER =
            new Converter<>() {
                @Override
                public Instant from(LocalDateTime databaseObject) {
                    return databaseObject == null ? null : databaseObject.toInstant(ZoneOffset.UTC);
                }

                @Override
                public LocalDateTime to(Instant userObject) {
                    return userObject == null ? null : LocalDateTime.ofInstant(userObject, ZoneOffset.UTC);
                }

                @Override
                @NonNull
                public Class<LocalDateTime> fromType() {
                    return LocalDateTime.class;
                }

                @Override
                @NonNull
                public Class<Instant> toType() {
                    return Instant.class;
                }
            };

    private static final Calendar UTC_CALENDAR = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

    @Override
    @NonNull
    public Converter<LocalDateTime, Instant> converter() {
        return CONVERTER;
    }

    @Override
    public void sql(BindingSQLContext<Instant> ctx) throws SQLException {
        if (ctx.render().paramType() == ParamType.INLINED)
            ctx.render().visit(DSL.inline(ctx.convert(converter()).value()));
        else
            ctx.render().sql("?");
    }

    @Override
    public void register(BindingRegisterContext<Instant> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), Types.TIMESTAMP);
    }

    @Override
    public void set(BindingSetStatementContext<Instant> ctx) throws SQLException {
        if (ctx.value() != null) {
            ctx.statement().setTimestamp(ctx.index(), Timestamp.from(ctx.value()), UTC_CALENDAR);
        } else {
            ctx.statement().setTimestamp(ctx.index(), null);
        }
    }

    @Override
    public void get(BindingGetResultSetContext<Instant> ctx) throws SQLException {
        Timestamp ts = ctx.resultSet().getTimestamp(ctx.index(), UTC_CALENDAR);
        ctx.value(ts != null ? ts.toInstant() : null);
    }

    @Override
    public void get(BindingGetStatementContext<Instant> ctx) throws SQLException {
        Timestamp ts = ctx.statement().getTimestamp(ctx.index(), UTC_CALENDAR);
        ctx.value(ts != null ? ts.toInstant() : null);
    }

    @Override
    public void set(BindingSetSQLOutputContext<Instant> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void get(BindingGetSQLInputContext<Instant> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}