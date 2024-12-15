//package org.poo.bankInput;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.poo.fileio.CommerciantInput;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Getter
//@Setter
//public class Commerciants {
//    private int id;
//    private String description;
//    private List<Commerciant> commerciants;
//
//    public static Commerciants fromInput(final CommerciantInput input) {
//        final Commerciants commerciantsGroup = new Commerciants();
//        commerciantsGroup.setId(input.getId());
//        commerciantsGroup.setDescription(input.getDescription());
//        commerciantsGroup.setCommerciants(input.getCommerciants()
//                .stream()
//                .map(Commerciant::new)
//                .collect(Collectors.toList()));
//        return commerciantsGroup;
//    }
//
//    public static List<Commerciants> fromInputList(final List<CommerciantInput> inputs) {
//        return inputs.stream()
//                .map(Commerciants::fromInput)
//                .collect(Collectors.toList());
//    }
//}
