package levykauppa.web;

import levykauppa.domain.Record;
import levykauppa.domain.RecordRepository;
import levykauppa.domain.FormatRepository;
import levykauppa.domain.GenreRepository;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RecordController {

    private RecordRepository recordRepository;
    private FormatRepository formatRepository;
    private GenreRepository genreRepository;

    public RecordController(RecordRepository recordRepository,
            FormatRepository formatRepository,
            GenreRepository genreRepository) {
        this.recordRepository = recordRepository;
        this.formatRepository = formatRepository;
        this.genreRepository = genreRepository;
    }

    // Home Page

    @GetMapping("/")
    public String home() {
        return "home";
    }
    // List Of Records

    @GetMapping("/recordlist")
    public String recordList(Model model) {
        List<Record> records = (List<Record>) recordRepository.findAll();

        long recordCount = records.size();
        double totalValue = records.stream()
                .mapToDouble(Record::getPrice)
                .sum();
        double avg = 0.0;
        if (recordCount > 0) {
            avg = totalValue / recordCount;
        }
        model.addAttribute("avgPrice", avg);

        model.addAttribute("records", records);
        model.addAttribute("recordCount", recordCount);
        model.addAttribute("totalValue", totalValue);

        return "recordlist";
    }

    // Add Record Form

    @GetMapping("/addrecord")
    public String showAddForm(Model model) {
        model.addAttribute("album", new Record());
        model.addAttribute("formats", formatRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return "addrecord";
    }

    // Save a record

    @PostMapping("/save")
    public String saveRecord(Record record) {
        recordRepository.save(record);
        return "redirect:/recordlist";
    }

    // Delete a record

    @GetMapping("/deleterecord/{id}")
    public String deleteRecord(@PathVariable Long id) {
        recordRepository.deleteById(id);
        return "redirect:/recordlist";
    }
}