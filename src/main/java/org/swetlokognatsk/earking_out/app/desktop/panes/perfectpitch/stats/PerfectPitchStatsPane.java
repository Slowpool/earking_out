package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.stats;

import org.swetlokognatsk.earking_out.app.desktop.panes.SessionStatsPane;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.PerfectPitchNoteStats;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.PerfectPitchSessionStats;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.perfectpitch.PerfectPitchSessionAggregateDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.perfectpitch.PerfectPitchSessionStatsService;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

// no further inheritance because stats are the same for both visual and audio exercise types
public final class PerfectPitchStatsPane<E extends PerfectPitchExercise, SADTO extends PerfectPitchSessionAggregateDTO<E, ?, ?, ?>> extends SessionStatsPane<E, SADTO, PerfectPitchSessionStatsService<E>> {

    public PerfectPitchStatsPane(final SADTO sessionDto, final PerfectPitchSessionStatsService<E> statsAggregator) {
        super(sessionDto, statsAggregator);
    }

    protected Pane buildStatsPane() {
        var perfectPitchStats = statsService.getAggregatedStats(sessionDto.sessionId);

        var table = buildStatsTable(perfectPitchStats);

        var pane = new VBox(new Label("some stats are here"), table);
        pane.setAlignment(Pos.CENTER);
        return pane;
    }

    protected TableView<PerfectPitchNoteStats> buildStatsTable(final PerfectPitchSessionStats<?> sessionStats) {
        var tableView = new TableView<PerfectPitchNoteStats>();

        var noteColumn = new TableColumn<PerfectPitchNoteStats, String>("Note");
        noteColumn.setCellValueFactory(cellData -> {
            var keyNumber = cellData.getValue()
                .note
                .value;
            return new SimpleObjectProperty<>(String.valueOf(keyNumber));
        });

        var appearancesColumn = new TableColumn<PerfectPitchNoteStats, Integer>("Appearances");
        appearancesColumn.setCellValueFactory(cellData -> {
            var appearances = cellData.getValue().numberOfAppearances;
            return new SimpleObjectProperty<>(appearances);
        });

        var allGuessesColumn = new TableColumn<PerfectPitchNoteStats, Integer>("Guesses");
        allGuessesColumn.setCellValueFactory(cellData -> {
            var allGuesses = cellData.getValue().numberOfAllGuesses;
            return new SimpleObjectProperty<>(allGuesses);
        });

        var perfectGuessesColumn = new TableColumn<PerfectPitchNoteStats, Integer>("Perfect guesses");
        perfectGuessesColumn.setCellValueFactory(cellData -> {
            var perfectGuesses = cellData.getValue().numberOfPerfectGuesses;
            return new SimpleObjectProperty<>(perfectGuesses);
        });

        var perfectGuessesRatioColumn = new TableColumn<PerfectPitchNoteStats, String>("Perfect guesses ratio");
        perfectGuessesRatioColumn.setCellValueFactory(cellData -> {
            var perfectGuessesRatio = cellData.getValue().perfectGuessesRatio * 100;
            var formattedRatio = "%.2f %%".formatted(perfectGuessesRatio);
            return new SimpleStringProperty(formattedRatio);
        });

        tableView.getColumns()
                .addAll(noteColumn, appearancesColumn, allGuessesColumn, perfectGuessesColumn, perfectGuessesRatioColumn);

        var notesStats = FXCollections.observableArrayList(sessionStats.notesStats);
        tableView.setItems(notesStats);

        return tableView;
    }
}
