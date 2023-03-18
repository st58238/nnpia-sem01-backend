package cz.upce.fei.janacek.lukas.migration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.TransactionCallbackWithoutResult
import org.springframework.transaction.support.TransactionTemplate
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.Executors
import java.util.function.Consumer
import java.util.stream.Collectors
import javax.annotation.PostConstruct


@Profile("migration")
@Service
final class MigrationInitService (
    @Qualifier("transactionManager") val txManager: PlatformTransactionManager,
    @Autowired(required = false) var ignoredSqlQueries: MutableList<String>?
) {
    // v PostConstruct resp. afterPropertiesSet nefunguje @Transactional. Vyresit se to da takto
    // (https://stackoverflow.com/questions/17346679/transactional-on-postconstruct-method)
    // Bez toho napr. nefunguje automaticke ukladani zmen v managovanych objektech, ale je potreba explicitne volat save()

    @Value("\${spring.datasource.url1}")
    private lateinit var appDbAddress: String

    @Value("\${spring.datasource.url2}")
    private lateinit var migrationDbAddress: String

    init {
        if (ignoredSqlQueries == null)
            ignoredSqlQueries = mutableListOf()
        ignoredSqlQueries!!.add("drop schema if exists \"sem01\";")
    }

    @PostConstruct
    fun postConstruct() {
        val template = TransactionTemplate(txManager)
        template.execute(object : TransactionCallbackWithoutResult() {
            override fun doInTransactionWithoutResult(status: TransactionStatus) {
                init()
            }
        })
    }

    private fun init() {
        val allDbName = "V${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"))}__migration.sql"
        val migrationFile = File(allDbName)
        val command = "/opt/homebrew/bin/migra $appDbAddress $migrationDbAddress --unsafe"
        println(command)
        val process: Process = Runtime.getRuntime().exec(command.split(" ").toTypedArray())
        val migration = mutableListOf<String>()
        val streamGobbler = StreamGobbler(process.inputStream) { s: String ->
            if (!s.contains("flyway") && !(ignoredSqlQueries?.any {
                    it.trim().lowercase() == s.trim().lowercase()
                } == true)) {
                migration.add(s)
            }
        }
        Executors.newSingleThreadExecutor().submit(streamGobbler)
        val exitCode = process.waitFor()
        assert(exitCode == 0)
        migration.removeIf { it.isEmpty() }
        val res = migration.stream()
            .collect(Collectors.joining("\n"))
        if (res.isNotEmpty() && res.isNotBlank())
            migrationFile.writeText("-- noinspection SqlResolveForFile\n\n$res")
    }

    private class StreamGobbler(private val inputStream: InputStream, private val consumer: Consumer<String>) : Runnable {
        override fun run() {
            BufferedReader(InputStreamReader(inputStream)).lines()
                .forEach(consumer)
        }
    }
}