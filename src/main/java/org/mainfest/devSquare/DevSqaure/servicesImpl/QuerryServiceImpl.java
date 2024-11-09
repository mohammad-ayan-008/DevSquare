package org.mainfest.devSquare.DevSqaure.servicesImpl;

import com.google.gson.Gson;
import org.bson.types.ObjectId;
import org.mainfest.devSquare.DevSqaure.entities.CommentDTO;
import org.mainfest.devSquare.DevSqaure.entities.Querry;
import org.mainfest.devSquare.DevSqaure.entities.USER;
import org.mainfest.devSquare.DevSqaure.entities.payloads;
import org.mainfest.devSquare.DevSqaure.repositories.QuerriesRepository;
import org.mainfest.devSquare.DevSqaure.repositories.UserRepository;
import org.mainfest.devSquare.DevSqaure.services.QuerryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class QuerryServiceImpl implements QuerryService {
    @Autowired
    private QuerriesRepository querriesRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Gson gson;
    @Autowired
    private Notificationservice notificationservice;
    @Override
    public Querry save(Querry querry,String name) {
        USER byUserName = userRepository.findByUserName(name);
        querry.setPosted_by(name);
        Querry save = querriesRepository.save(querry);
        if (byUserName != null){
            byUserName.getQuerries().add(save);
            userRepository.save(byUserName);
        }
        return  save;
    }

    @Override
    public Querry delete(String id,String name) {
        List<Querry> collect = userRepository.findByUserName(name).getQuerries().stream().filter(querry -> id.equals(querry.get_id())).collect(Collectors.toList());
        if(querriesRepository.findById(id).isPresent() && !collect.isEmpty()) {
            querriesRepository.deleteById(id);
            return collect.get(0);
        }
        return null;
    }

    @Override
    public Querry update(Querry querry,String name,String id) {
        ArrayList<Querry> querries = userRepository.findByUserName(name).getQuerries();
        List<Querry> collect = querries.stream().filter(querry1 -> querry1.get_id().equals(id)).toList();
        if (querriesRepository.findById(querry.get_id()).isPresent() && !collect.isEmpty()){
            return querriesRepository.save(querry);
         }
         return new Querry();
    }

    @Override
    public List<Querry> fetchAll(String name) {
        return userRepository.findByUserName(name).getQuerries();
    }

    @Override
    public Querry fetchByID(String id) {
        return querriesRepository.findById(id).orElse(null);
    }

    @Override
    public Querry uploadComment(String id, CommentDTO commentDTO) {
        Optional<Querry> querry = querriesRepository.findById(id);
        if (querry.isPresent()){
            Querry querry1 = querry.get();
            String mentioned_name = hasMention(commentDTO.getReply());
            if (mentioned_name != null){
                payloads pls = new payloads(mentioned_name,querry1);
                notificationservice.sendMentionedNotification(pls);
            }
            querry1.getReplies().add(commentDTO);
            return querriesRepository.save(querry1);
        }
        return null;
    }

    @Override
    public Page<Querry> getAllQuerries(int offset, int page) {
        return querriesRepository.findAll(PageRequest.of(offset,page));
    }

    @Override
    public boolean deleteComment(String comment_id,String querry_id) {
        Optional<Querry> querry  = querriesRepository.findById(querry_id);
        if(querry.isPresent()){
            List<CommentDTO> list_querry = querry.get().getReplies().stream().
                    filter(replies -> !replies.getId().equals(comment_id)).toList();
            querry.get().setReplies(list_querry);
            Querry querry1 =  querriesRepository.save(querry.get());
            return true;
        }
        return false;
    }

    @Override
    public Querry helpful_btn(String post_id, String name) {
        Optional<Querry> Opquerry = querriesRepository.findById(post_id);
        if (Opquerry.isPresent()) {
            Querry querry = Opquerry.get();
            if(querry.getHelpFull().contains(name)) querry.getHelpFull().remove(name);
            else querry.getHelpFull().add(name);
            return querriesRepository.save(querry);
        }
        return null;
    }

    @Override
    public void deleteAll() {
        querriesRepository.deleteAll();
    }

    private static String hasMention(String comment) {
        Pattern pattern = Pattern.compile("@(\\w+)");
        Matcher matcher = pattern.matcher(comment);

        boolean foundMention = false;
        String name = "";

        while (matcher.find()) {
            if (matcher.groupCount() >= 1) {
                name = matcher.group(1);
                foundMention = true;
            }
        }

        return foundMention ? name : null;
    }
}
