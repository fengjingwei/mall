package org.hengxunda.springcloud.order.j8;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Track implements Serializable {

    private static final long serialVersionUID = 8647690440751700913L;

    private String name;

    private Integer count;
}
