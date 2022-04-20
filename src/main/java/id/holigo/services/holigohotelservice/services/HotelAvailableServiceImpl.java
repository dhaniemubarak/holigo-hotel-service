package id.holigo.services.holigohotelservice.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import id.holigo.services.holigohotelservice.domain.HotelAddresses;
import id.holigo.services.holigohotelservice.repositories.HotelAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import id.holigo.services.holigohotelservice.domain.Cities;
import id.holigo.services.holigohotelservice.domain.HotelAvailable;
import id.holigo.services.holigohotelservice.domain.Hotel;
import id.holigo.services.holigohotelservice.repositories.CitiesRepository;
import id.holigo.services.holigohotelservice.repositories.HotelAvailableRepository;
import id.holigo.services.holigohotelservice.repositories.HotelRepository;
import id.holigo.services.holigohotelservice.repositories.spesification.GenericOrSpecification;
import id.holigo.services.holigohotelservice.repositories.spesification.GenericSpecification;
import id.holigo.services.holigohotelservice.repositories.spesification.SearchCriteria;
import id.holigo.services.holigohotelservice.repositories.spesification.SearchOperation;
import id.holigo.services.holigohotelservice.web.mappers.HotelMapper;
import id.holigo.services.holigohotelservice.web.model.DetailHotelForListDto;
import id.holigo.services.holigohotelservice.web.model.HotelAvailablePaginateForUser;
import id.holigo.services.holigohotelservice.web.model.detailHotel.HotelDto;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HotelAvailableServiceImpl implements HotelAvailableService {

        @Autowired
        private HotelAvailableRepository hotelAvailableRepository;

        @Autowired
        private HotelAddressRepository hotelAddressRepository;

        @Autowired
        private HotelRepository hotelRepository;

        @Autowired
        private CitiesRepository citiesRepository;

        @Autowired
        private HotelMapper hotelMapper;

        public HotelDto getDetailHotelAvailable(Long id) {
                // MASIH DUMMY
                // HotelPolicyDto hotelPolicy = HotelPolicyDto.builder().longPolicy(
                // "Hewan peliharaan diizinkan , biaya tambahan berlaku. Hotel memperbolehkan
                // check-in awal, bergantung pada ketersediaan kamar, dengan biaya tambahan.
                // Hotel memperbolehkan check-out lebih lama, bergantung pada ketersediaan
                // kamar, dengan biaya tambahan. Hotel ini akan mengenakan biaya tambahan untuk
                // transportasi sebesar IDR 330,000.NOTICE: TEMPORARY CLOSURE & UPDATE OF PUBLIC
                // FACILITIES To support and follow the Large Scale Social Restriction
                // regulation from the local authorities and to prioritize the health and safety
                // for all our guests, please be informed that the facilities: Gym & Fitness
                // Center, Swimming Pool, Cubbies Playground, Spa and Sauna will be temporary
                // closed until further notice. Restaurant will open from 06.00 AM to 10.30 PM
                // (Monday - Friday) and 07.00 AM to 11.00 PM (Saturday - Sunday). Breakfast
                // will be delivered to the room, restaurant not opened for dine-in, and only
                // serving in room dining & take away. And facilities noticement: free parking
                // listed is only applicable for one car per unit. We apologize for any
                // inconvenience caused. Thank you for your understanding.")
                // .shortPolicy(
                // "Hewan peliharaan diizinkan , biaya tambahan berlaku. Hotel memperbolehkan
                // check-in awal, bergantung pada ketersediaan kamar, dengan biaya tambahan.")
                // .build();
                // HotelDetailInformationDto detailInformationDto =
                // HotelDetailInformationDto.builder()
                // .headerUrl("https://imagekit.io/holigo/XXX.png")
                // .illustration("https://imagekit.io/holigo/XXX.png")
                // .title("Apa itu InDOnesia CARE?").body("\nSelamat Datang.").build();
                // HotelInformationDto hotelInformationDto =
                // HotelInformationDto.builder().imageUrl("https://XXX.co")
                // .title("InDOnesia CARE")
                // .subtitle("Akomodasi ini bersih, aman, dan tersertifikasi CHSE.")
                // .detail(detailInformationDto).build();

                HotelDto hotelDto = new HotelDto();
                // hotelDto.setId(id);
                // hotelDto.setName("Ascott Sudirman Jakarta");
                // hotelDto.setType("Hotel");
                // hotelDto.setRating(5.0);
                // List<String> additionalInformation = Arrays.asList("8 Orang baru saja
                // melakukan booking!",
                // "25 Orang melihat hotel ini!");
                // hotelDto.setAdditionalInformations(additionalInformation);
                // hotelDto.setPolicy(hotelPolicy);
                // hotelDto.setHotelInformation(hotelInformationDto);
                log.info("Hotels ID -> {}", id);
                Optional<Hotel> hotel = hotelRepository.findById(id);
                if (hotel.isPresent()) {
                        log.info("Response Hotel -> {}", hotel.get().getFacilities());
                        hotelDto = hotelMapper.hotelsToHotelDto(hotel.get());
                        return hotelDto;
                } else {
                        return null;
                }

        }

        @Override
        public HotelAvailablePaginateForUser listHotelForUser(Long destination, PageRequest pageRequest,
                        String rating, String facilities, String types, Date checkIn, Date checkOut) {
                HotelAvailablePaginateForUser hotelAvailablePaginateForUser;
                Page<HotelAvailable> hotelPage;
                GenericSpecification<HotelAvailable> andSpecification = new GenericSpecification<HotelAvailable>();
                GenericOrSpecification<HotelAvailable> orSpecification = new GenericOrSpecification<HotelAvailable>();
                log.info("City Id -> {}", destination);
                Optional<Cities> city = citiesRepository.findById(destination);

                if (city.isEmpty()) {
                        return null;
                }

                andSpecification.add(new SearchCriteria("cityId", city.get(), SearchOperation.EQUAL));
                andSpecification.add(new SearchCriteria("checkIn", checkIn, SearchOperation.EQUAL));

                // orSpecification.add(new SearchCriteria("cityId", city.get(),
                // SearchOperation.EQUAL));

                if (rating != null && !rating.equals("")) {
                        List<String> ratings = new ArrayList<String>(Arrays.asList(rating.split(",")));
                        if (ratings.size() <= 0) {
                                andSpecification
                                                .add(new SearchCriteria("rating", rating,
                                                                SearchOperation.MATCH));
                        }
                        for (String starRating : ratings) {
                                andSpecification
                                                .add(new SearchCriteria("rating", Double.valueOf(starRating),
                                                                SearchOperation.EQUAL));
                        }
                }

                if (facilities != null && !facilities.equals("")) {
                        List<String> facility = new ArrayList<String>(Arrays.asList(facilities.split(",")));
                        if (facility.size() <= 0) {
                                andSpecification
                                                .add(new SearchCriteria("facilities", facilities,
                                                                SearchOperation.MATCH));
                        }

                        for (String fclty : facility) {
                                andSpecification
                                                .add(new SearchCriteria("facilities", fclty, SearchOperation.MATCH));
                        }
                }

                if (types != null && !types.equals("")) {
                        List<String> listType = new ArrayList<String>(Arrays.asList(types.split(",")));
                        if (listType.size() <= 0) {
                                orSpecification.add(new SearchCriteria("type", types, SearchOperation.EQUAL));
                        }
                        for (String hotelType : listType) {
                                log.info("Cari Tipe Kamar -> {}", hotelType);
                                orSpecification.add(new SearchCriteria("type", hotelType, SearchOperation.EQUAL));
                        }
                }
                hotelPage = hotelAvailableRepository.findAll(Specification.where(orSpecification).and(andSpecification),
                                pageRequest);

                hotelAvailablePaginateForUser = new HotelAvailablePaginateForUser(
                                hotelPage.getContent().stream().map(hotelMapper::hotelAvailableToDetailHotelForUserDto)
                                                .toList(),
                                PageRequest.of(hotelPage.getPageable().getPageNumber(),
                                                hotelPage.getPageable().getPageSize()),
                                hotelPage.getTotalElements());

                return hotelAvailablePaginateForUser;
        }

        @Override
        public void postHotelAvailable(DetailHotelForListDto detailHotelForListDto, Integer cityId) {
                HotelAvailable hotelAvailable = hotelMapper.detailHotelDtoToHotelAvailable(detailHotelForListDto, cityId);
                Optional<HotelAvailable> fetchHotelAvailable = hotelAvailableRepository
                                .findByNameAndCheckIn(hotelAvailable.getName(), hotelAvailable.getCheckIn());
                if (fetchHotelAvailable.isEmpty()) {
                        hotelAvailableRepository.save(hotelAvailable);
                }
        }

        @Override
        @Transactional
        public List<DetailHotelForListDto> generateAvailableHotel(Date checkIn, Integer cityId) {
                Optional<Cities> city = citiesRepository.findById(Long.valueOf(cityId));
                if(city.isEmpty()){
                        return null;
                }
                List<HotelAddresses> hotelAddresses = hotelAddressRepository.findAllByCity(city.get());
                List<Long> listHotelId = new ArrayList<>();
                hotelAddresses.forEach(listAddress -> {
                        listHotelId.add(listAddress.getHotel().getId());
                });
                List<Hotel> listHotel = hotelRepository.findAllByIdIn(listHotelId);
                List<HotelDto> listHotelDto = listHotel.stream().map(hotelMapper::hotelsToHotelDto).toList();
                List<DetailHotelForListDto> listDetailHotel = listHotelDto.stream()
                                .map(hotelMapper::hotelDtoToDetailHotelForListDto).toList();
                for (DetailHotelForListDto detailHotelForListDto : listDetailHotel) {
                        log.info("Nama hotel -> {}", detailHotelForListDto.getName());
                        detailHotelForListDto.setCheckIn(checkIn);
                        postHotelAvailable(detailHotelForListDto, cityId);
                }
                return listDetailHotel;
        }

}
