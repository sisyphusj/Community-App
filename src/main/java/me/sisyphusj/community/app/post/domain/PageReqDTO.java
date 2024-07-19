package me.sisyphusj.community.app.post.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageReqDTO {

    @NotNull
    private Integer page;

    @NotNull
    private PageSortType sort;

}
