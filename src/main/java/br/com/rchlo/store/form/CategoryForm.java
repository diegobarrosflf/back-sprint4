package br.com.rchlo.store.form;

import br.com.rchlo.store.domain.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CategoryForm {

    @NotBlank
    @Size(max = 255, min = 3)
    private  String name;

    @NotBlank
    @Pattern(regexp = "[a-z0-9\\-]{3,255}")
    private  String slug;

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Category converter() {
        return new Category(name, slug);
    }
}
